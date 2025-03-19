package com.addalia.simplemonitor.services;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.addalia.simplemonitor.controllers.context.ApplicationPropertiesConfig;
import com.addalia.simplemonitor.controllers.context.StaticData;
import com.addalia.simplemonitor.dms.dto.SearchParameters;
import com.addalia.simplemonitor.dms.dto.SearchResponse;
import com.addalia.simplemonitor.dms.service.DmsRestService;
import com.addalia.simplemonitor.services.dto.DmsConfig;
import com.addalia.simplemonitor.services.dto.DmsInstallationDto;
import com.addalia.simplemonitor.services.dto.DmsInstallationQuery;
import com.addalia.simplemonitor.services.dto.DmsQuery;
import com.addalia.simplemonitor.services.dto.RetrievedDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DmsDataCollectorService {

	@Autowired
	ApplicationPropertiesConfig applicationProperties;

	private static Logger logger = LoggerFactory.getLogger(DmsDataCollectorService.class);

	public void run(boolean forceDataCollection) {

		if (forceDataCollection) {
			collectData();
		} else { 
			if (mustCollectData()) {
				collectData();
			}
		}

	}

	private boolean mustCollectData() {
		//Review the elapsed time since the last data query to the configured DMS's.
		long currentTime = System.currentTimeMillis();
		if (currentTime - StaticData.retrievedDataDto.getLastRun() > applicationProperties.getTimeBetweenExecutions()) {
			StaticData.retrievedDataDto.setLastRun(currentTime);
			return true;
		}
		return false;
	}

	private void collectData() {

		try {
			// Load configuration file
			File resource = new ClassPathResource("queriescfg.json").getFile();
			//String text = new String(Files.readAllBytes(resource.toPath()));
			String text = new String(Files.readAllBytes(resource.toPath()), StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();
			QueriesConfiguration config = objectMapper.readValue(text, QueriesConfiguration.class);

			for (DmsConfig dms : config.getDmsList()) {
				process(dms);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private void process(DmsConfig dms) {

		DmsInstallationDto installation = new DmsInstallationDto();
		installation.setInstallationName(dms.getDmsName());
		installation.setId(dms.getConfigId());

		try {
			DmsRestService dmsService = new DmsRestService(dms.getDmsUrl(),
					applicationProperties.getIgnoreInvalidSSLCertificates(), dms.getDmsName());
			boolean connected = dmsService.connect(dms.getDmsUser(), dms.getDmsPass());
			if (connected) {
				for (DmsQuery query : dms.getQueries()) {

					DmsInstallationQuery installationQuery = new DmsInstallationQuery();
					installationQuery.setQueryNameOrDescription(query.getName());

					SearchResponse search = dmsService
							.searchDocuments(new SearchParameters(query.getQuery(), 0, 0, true));
					long totalDocuments = search.getMeta().getTotal();
					String msg = dms.getDmsName() + " -> '" + query.getQuery() + "' -> " + totalDocuments;
					logger.info(msg);

					installationQuery.setTotalDocuments(totalDocuments);

					installation.getQueries().add(installationQuery);
				}

				dmsService.disconnect();
			}
			
			updateStaticData(installation);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private void updateStaticData(DmsInstallationDto installation) {
		boolean found = false;
		for (int i=0; i < StaticData.retrievedDataDto.getServices().size(); i++) {
			if (StaticData.retrievedDataDto.getServices().get(i).getId() == installation.getId()) {
				StaticData.retrievedDataDto.getServices().set(i, installation);
				found = true;
			}
		}
		if (!found) {
			StaticData.retrievedDataDto.getServices().add(installation);
		}
	}

}
