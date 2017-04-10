package com.gusi.demo.nosql.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoBusiness {
	@Autowired
	private MongoTemplate mongoTemplate;
}
