package service;

import java.net.URI;
import java.util.concurrent.Future;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

@Component
public class Service {
	
	public  String getAll(){
		ResponseEntity<String> entity=send(null,HttpMethod.GET,"http://localhost:8803/getCard");
		return entity.getBody();
	}
	
	private  ResponseEntity<String> send(Object body, HttpMethod method, String urlPath){
		URI url=URI.create(urlPath);
		RequestEntity<Object> request=new RequestEntity<Object>(body, method, url);
		RestTemplate restTemplate=new RestTemplate();
		return restTemplate.exchange(request, String.class);
	}
	
	@Async
	public void testSync(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println(Thread.currentThread().getName()+"over!!!");
		int i=1/0;
	}
	@Async
	public Future<String> testSyncReturn(){
		try {
			System.out.println(Thread.currentThread().getName()+"over!!!");
			Thread.sleep(2000);
			return new AsyncResult<String>(getAll());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	
	@Async
	public ListenableFuture<String> testSyncReturn2(){
		try {
			System.out.println(Thread.currentThread().getName()+"over!!!");
			Thread.sleep(2000);
			return new AsyncResult<String>(getAll());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
	

}
