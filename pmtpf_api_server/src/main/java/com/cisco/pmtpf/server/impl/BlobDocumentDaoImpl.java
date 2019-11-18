package com.cisco.pmtpf.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cisco.pmtpf.server.commons.BaseCounter;
import com.cisco.pmtpf.server.dao.BlobDocumentDao;
import com.cisco.pmtpf.server.model.BlobDocument;
import com.mongodb.client.result.DeleteResult;

@Repository
public class BlobDocumentDaoImpl implements BlobDocumentDao<BlobDocument> {

	@Autowired
	MongoOperations mongoOperation;

	@Autowired
	BaseCounter counter;

	@Override
	public BlobDocument saveDocument(BlobDocument blobDocument) {
		long nextSequenceId = counter.sequence(BlobDocument.Fields.inputFileId.name(), BlobDocument.class);
		System.out.println(nextSequenceId);
		blobDocument.setInputFileId(nextSequenceId);
		BlobDocument doc = mongoOperation.save(blobDocument);
		return doc;
	}

	@Override
	public BlobDocument updateDocument(BlobDocument document) {
		BlobDocument doc = mongoOperation.save(document);
		return doc;
	}

	@Override
	public boolean deleteDocument(long docId) {
		BlobDocument doc = getDoument(docId);
		DeleteResult result = mongoOperation.remove(doc);
		if (result.getDeletedCount() > 0)
			return true;
		return false;
	}

	@Override
	public BlobDocument getDoument(long docId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(docId));
		BlobDocument doc = mongoOperation.findOne(query, BlobDocument.class);
		return doc;
	}

	public BlobDocument includeAutogenerateId(BlobDocument blob) {
		long count = 0;
		if (!mongoOperation.collectionExists(blob.getClass()))
			blob.setInputFileId(1L);
		else {
			// Get total count of documents..
			count = mongoOperation.count(new Query(), mongoOperation.getCollectionName(BlobDocument.class));
			Query q = new Query();
			q.fields().include("_id");
			BlobDocument entity = mongoOperation.findOne(q, blob.getClass());
			Long entityId = entity.getInputFileId();
			if (entityId > count)
				blob.setInputFileId(entityId + 1);
			else
				blob.setInputFileId(count + 1);
			entity = null;
		}
		return blob;
	}

}
