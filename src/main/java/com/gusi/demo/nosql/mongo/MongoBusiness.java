package com.gusi.demo.nosql.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

@Service
public class MongoBusiness {
	@Autowired
	private MongoTemplate mongoTemplate;// 直接在业务类中注入template对象

	/**
	 * mongo处理对象
	 *
	 */
	@Document(collection = "demoColl")
	public static class Demo {
		@Id
		private String a;
		private int b;

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		@Override
		public String toString() {
			return "Demo [a=" + a + ", b=" + b + "]";
		}

	}

	public void demoMongo() {
		// 获取到DBCollection以后就可以对其进行各种操作
		DBCollection coll = mongoTemplate.getCollection("coll1");

		// 也可以异步的回调执行
		boolean isExist = mongoTemplate.execute(new DbCallback<Boolean>() {

			public Boolean doInDB(DB db) throws MongoException, DataAccessException {
				return db.collectionExists("coll1");
				// return null;
			}
		});
		System.out.println(isExist);

		// 通过template对demo对象各种操作
		Demo demo = new Demo();
		demo.setA("aaa");
		demo.setB(222);
		// 直接插入对象数据
		mongoTemplate.save(demo, "coll1");
		mongoTemplate.save(demo);// 不指定collectoin就使用类名作为默认的collcetionName

		// 修改数据
		WriteResult result = mongoTemplate.updateMulti(new Query(Criteria.where("b").is(222)), Update.update("a", "kkk"), "coll1");
		System.out.println(result.getN());
		// 这里还可以修改数组数据等等

		// 查询数据
		demo = mongoTemplate.findOne(new Query(Criteria.where("b").is(222)), Demo.class, "coll1");
		System.out.println(demo);
		// 这里的查询还有很多种可以使用

		// 删除数据
		// mongoTemplate.dropCollection("coll1");

		// mongoTemplate.XXX()//还有很多方法功能，比如创建索引等等都可以直接通过template完成
	}
}
