package com.addalia.simplemonitor.dms.dto;

import java.util.ArrayList;

public class DmsVolumes {

	private ArrayList<DmsVolumesVolume> volumes;
	private DmsVolumesMeta meta;

	public ArrayList<DmsVolumesVolume> getVolumes() {
		return volumes;
	}

	public void setVolumes(ArrayList<DmsVolumesVolume> volumes) {
		this.volumes = volumes;
	}

	public DmsVolumesMeta getMeta() {
		return meta;
	}

	public void setMeta(DmsVolumesMeta meta) {
		this.meta = meta;
	}

}
