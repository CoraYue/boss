package com.huying.bos.dao.base;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huying.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

	@Autowired
	private StandardRepository standardRepository;

	// 查询所有的派送标准
	@Test
	public void test() {
		List<Standard> list = standardRepository.findAll();
		for (Standard standard : list) {
			System.out.println(standard);
		}

	}

	//增加
	@Test
	public void test2() {
		Standard standard = new Standard();
		standard.setName("王五五");
		standard.setMaxWeight(100);
		standardRepository.save(standard);

	}

	// 修改
	@Test
	public void test3() {
		Standard standard = new Standard();
		standard.setId(5L);
		standard.setName("李四2");
		standard.setMaxWeight(200);

		// save兼具保存和修改的功能,
		// 修改的话,必须传入id
		standardRepository.save(standard);
	}

	// 查询第二条数据
	@Test
	public void test4() {
		Standard standard = standardRepository.findOne(2L);
		System.out.println(standard);

	}
	
	//删除
	@Test
	public void test5() {
		standardRepository.delete(2L);

	}
	
	 // 根据名字进行查找
	@Test
    public void test6() {
        List<Standard> list = standardRepository.findByName("张三");
        for (Standard standard : list) {
            System.out.println(standard);
        }

    }
	
	//模糊查询
	@Test
	public void test7() {
		List<Standard> list = standardRepository.findByNameLike("%李四%");
		for (Standard standard : list) {
			System.out.println(standard);
		}
	}
	
	//根据名字和最大重量查找
		@Test
		public void test8() {
			List<Standard> list = standardRepository.findByNameAndMaxWeight("张三", 100);
			for (Standard standard : list) {
				System.out.println(standard);
			}
		}

		 @Test
		    public void test9() {
		        // 自定义方法名
		        List<Standard> list =
		                standardRepository.findByNameAndMaxWeight321312("张三", 100);
		        for (Standard standard : list) {
		            System.out.println(standard);
		        }

		    }
		 
		 @Test
		    public void test10() {
		        // 自定义方法
		        List<Standard> list =
		                standardRepository.findByNameAndMaxWeight321312(100, "张三");
		        for (Standard standard : list) {
		            System.out.println(standard);
		        }

		    }
		 
		//自定义方法
			@Test
			public void test11() {
				List<Standard> list = standardRepository.findByNameAndMaxWeight321312("张三", 100);
				for (Standard standard : list) {
					System.out.println(standard);
				}
			}
			
			//修改update
			// 在测试用例中使用事务注解,方法执行完成以后,事务回滚了
			 @Test
			    public void test12() {
			        standardRepository.updateWeightByName(300,"张三");
			    }
		//自定义删除方法
			 @Test
			    public void test13() {
			        standardRepository.deleteByName("李四2");
			    }
}
