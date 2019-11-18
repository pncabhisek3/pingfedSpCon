package com.cisco.pmtpf.server.dao;

public interface BlobDocumentDao<T> {

	public T saveDocument(T blobDocument);

	public T updateDocument(T document);

	public boolean deleteDocument(long docId);
	
	public T getDoument(long docId);
	
}
