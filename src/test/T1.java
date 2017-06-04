package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import service.Service;

public class T1 {
	private static StringBuilder myjson=new StringBuilder();
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		String[] locations = {"applicationContext.xml"};  
        ApplicationContext context = new ClassPathXmlApplicationContext(locations);  
        Service s =  (Service) context.getBean("service"); 
     //   s.testSync();
        
       /* Future<String> future1= s.testSyncReturn();
        Future<String> future2= s.testSyncReturn();
        while (true) {  ///这里使用了循环判断，等待获取结果信息  
            if (future1.isDone()&&future2.isDone()) {  //判断是否执行完毕  
                try {
					System.out.println("Result from asynchronous process - " + future1.get()+future2.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
                break;  
            }  
            System.out.println("Continue doing something else. ");  
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  */
        ListenableFuture<String> listenableFuture =s.testSyncReturn2();
        SuccessCallback<String> successCallback =new SuccessCallback<String>(){

			@Override
			public void onSuccess(String str) {
				myjson.append(str);
				 System.out.println("异步回调成功了, return : " + str);  
			}};
        FailureCallback failureCallback = new FailureCallback() {

			@Override
			public void onFailure(Throwable throwable) {
				  System.out.println("异步回调失败了, exception message : " + throwable.getMessage());  
			}};
			listenableFuture.addCallback(successCallback, failureCallback);  
			while(true){
				if(listenableFuture.isDone()){					
					System.out.println(Thread.currentThread().getName()+"over!!!"+myjson.toString()+"132156156");
				    break;
				}
				System.out.println("--------------doing something------------");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}

}
