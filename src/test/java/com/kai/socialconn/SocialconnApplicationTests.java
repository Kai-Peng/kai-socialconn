package com.kai.socialconn;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.bean.TypeEnum;
import com.kai.socialconn.service.PurchaseService;
import com.kai.socialconn.service.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
//使用随机端口启动测试服务
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialconnApplicationTests {
	@Autowired
	private PurchaseService purchaseService;

	@MockBean
	//代码中注解@MockBean代表对哪个SpringBean使用Mock测试
	private TaskService taskService = null;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
		Assert.assertEquals(purchaseService.purchase(1L, 1L, 1),true);
	}

	@Test
	public void testGetTaskList(){
		List<Task> taskList = this.restTemplate.getForObject("/", List.class);
		Assert.assertNotNull(taskList);
	}

	@Test
	public void testMockTaskList(){
		List<Task> tasks = new ArrayList<>();
		Task task = new Task();
		task.setId("1");
		task.setType(TypeEnum.EATOUT);
		task.setModifyUser("kai");
		task.setTime("2018-12-17 16:41:00");
		task.setInfo("Eat out with friends on the weekend.");
		tasks.add(task);

		//指定Mock Bean方法和参数
		BDDMockito.given(this.taskService.getTaskList("user peng kai"))
				//指定返回的虚拟对象
			.willReturn(tasks);

		//进行Mock测试
		List<Task> taskList = this.taskService.getTaskList("user peng kai");
		Assert.assertTrue(taskList.get(0).getId().equals("1"));
	}

}
