package com.addalia.simplemonitor.dms.dto;

public class DmsVolumesVolume {

	private String id;
	private String name;
	private String description;
	private boolean mounted;
	private String mountPoint;
	private String repositoryPath;
	private boolean usable;
	private String totalSpace;
	private String usedSpace;
	private String maxDirectories;
	private String maxFiles;
	private String maxFileSize;
	private String creationTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMounted() {
		return mounted;
	}

	public void setMounted(boolean mounted) {
		this.mounted = mounted;
	}

	public String getMountPoint() {
		return mountPoint;
	}

	public void setMountPoint(String mountPoint) {
		this.mountPoint = mountPoint;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public String getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}

	public String getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(String usedSpace) {
		this.usedSpace = usedSpace;
	}

	public String getMaxDirectories() {
		return maxDirectories;
	}

	public void setMaxDirectories(String maxDirectories) {
		this.maxDirectories = maxDirectories;
	}

	public String getMaxFiles() {
		return maxFiles;
	}

	public void setMaxFiles(String maxFiles) {
		this.maxFiles = maxFiles;
	}

	public String getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

}
