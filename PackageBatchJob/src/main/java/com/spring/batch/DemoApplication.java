package com.spring.batch;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableBatchProcessing
public class DemoApplication {
	
	public static final String select_sql="select order_id,first_name,last_name,email,cost "
			+ ",item_id,item_name,purchase_date from shipped_order order by order_id";
	public static final String insert_sql="insert into shipping_order_output( order_id,"
			+ "first_name,last_name,email,cost,item_id,item_name,"
			+ "purchase_date)values"
			+ "(:orderId,:firstName,:lastName,:email,:cost,:itemId,:itemName,:purchaseDate)";
	
    @Autowired
	private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public StepExecutionListener selectFlowerLister() {
		return new FlowerSelectionListener();
	}
	
	@Bean
	public JobExecutionDecider deliveryDecider() {
		return new DeliveryDecider();
	}
	
	@Bean
	public JobExecutionDecider recieptDecider() {
		return new ReceiptDecider();
	}
	
	//@Bean
	public Job packageDeliverJob() {
		return this.jobBuilderFactory.get("packageDeliverJob").start(packageItemStep())
				//.stop()
//				.from(driveToAddressStep()).on("*").to(deliveryDecider())
//				.on("PRESENT").to(givePackageToCustomerStep()).next(recieptDecider()).on("CORRECT").to(thankCustomerStep())
//				.from(recieptDecider()).on("INCORRECT").to(refundStep()).from(deliveryDecider())
//				.on("NOT_PRESENT").to(leaveAtDoorStep()).end()
//				.build();
				.on("*").to(deliveryFlow()).next(nestedBillingStep()).end().build();
	}
     @Bean
	public Step packageItemStep() {

		return this.stepBuilderFactory.get("packageItemsStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            //String item=chunkContext.getStepContext().getJobParameters().get("items").toString();
           // String date=chunkContext.getStepContext().getJobParameters().get("run.date").toString();
           // System.out.println(String.format("The item %s has been packaged on date %s", item,date));
				System.out.println("Item has been packaged");
				return RepeatStatus.FINISHED;
			}
			
			
		}).build();
	}
	
     @Bean
     public Step driveToAddressStep() {
    	 boolean GOT_LOST=false;
    	
     return this.stepBuilderFactory.get("driveToAddressStep").tasklet(new Tasklet() {

		@Override
		public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
			 if(GOT_LOST)
	    		 throw new RuntimeException("Got lost");
			System.out.println("Successfully reached address");
			return RepeatStatus.FINISHED;
		}
    	 
     }).build();
     }
	@Bean
	public Step givePackageToCustomerStep() {
		return this.stepBuilderFactory.get("givePackageToCustomerStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
System.out.println("Given package to customer");
				return RepeatStatus.FINISHED;
			}}).build();
	}
	//Step-store package step.
	@Bean
	public Step storePackageStep() {
		return this.stepBuilderFactory.get("storePackageStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
 System.out.println("Storing package while customer address is located");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	//Step 5:-customer not home
	@Bean
	public Step leaveAtDoorStep() {
		return this.stepBuilderFactory.get("leaveAtDoorStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
System.out.println("Leaving package at door step");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Step refundStep() {
		return this.stepBuilderFactory.get("refundStep").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
System.out.println("Refunding customer money");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Step thankCustomerStep() {
		return this.stepBuilderFactory.get("thankCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
System.out.println("Thanking customer");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	/**Flower preparation step**/
	
	//@Bean
	public Job prepareFlowers() {
		return this.jobBuilderFactory.get("prepareFlowersJob").start(selectFlowers())
				.on("TRIM_REQD").to(removeThorns()).next(arrangeFlowers())
				.from(selectFlowers()).on("NO_TRIM_REQ").to(arrangeFlowers()).on("*")
				.to(deliveryFlow()).
				end()
				.build();
	}
	
	@Bean
	public Step selectFlowers() {
		return this.stepBuilderFactory.get("selectFlowers").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
              String flowerType=chunkContext.getStepContext().getJobParameters().get("type").toString();
				System.out.println(String.format("The flower type is: %s", flowerType));
				return RepeatStatus.FINISHED;
			}
			
		}).listener(selectFlowerLister()).build();
	}
	
	@Bean
	public Step removeThorns() {
		return this.stepBuilderFactory.get("removeThorns").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Removing thorns");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	@Bean
	public Step arrangeFlowers() {
		return this.stepBuilderFactory.get("arrangeFlowers").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Arranging flower for delivery");
				return RepeatStatus.FINISHED;
			}
			
		}).build();
	}
	
	//Nested jobs and External flow
	public Flow deliveryFlow() {
		return new FlowBuilder<SimpleFlow>("deliveryFlow")
				.start(driveToAddressStep())
				.on("FAILED").stop()
				.from(driveToAddressStep()).on("*").to(deliveryDecider())
				.on("PRESENT").to(givePackageToCustomerStep()).next(recieptDecider()).on("CORRECT").to(thankCustomerStep())
				.from(recieptDecider()).on("INCORRECT").to(refundStep()).from(deliveryDecider())
				.on("NOT_PRESENT").to(leaveAtDoorStep()).build();
	}
	
	//@Bean
	public Job billingJob(){
		return this.jobBuilderFactory.get("billingJob")
				.start(sendInvoiceStep()).build();
	}
	@Bean
	public Step sendInvoiceStep() {
		// TODO Auto-generated method stub
		return this.stepBuilderFactory.get("sendInvoiceStep")
				.tasklet(new Tasklet() {

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
							throws Exception {
System.out.println("Invoice send to customer");
						return RepeatStatus.FINISHED;
					}
					
				}).build();
	}
	
	@Bean
	public Step nestedBillingStep() {
		return this.stepBuilderFactory.get("nestedBillingStep").job(billingJob()).build();
	}
	
	
	//Chunk Processing-ItemReader,ItemProcessor,ItemWriter
	
	//1)ItemReader
	
	@Bean
	public Job chunkBasedJob() {
		return this.jobBuilderFactory.get("chunkBasedJob").start(chunkBasedStep()).build();
	}

	@Bean
	public Step chunkBasedStep() {
		return 
				this.stepBuilderFactory.get("chunkBasedStep")
				.<Order,Order>chunk(3).reader(itemReader()).writer(itemWriter())
				.build()
				
				;
	}

	@Bean
	public ItemWriter<? super Order> itemWriter() {

		return new JdbcBatchItemWriterBuilder<Order>()
				.dataSource(dataSource)
				.sql(insert_sql).beanMapped().build();
	}

	public ItemReader< Order> itemReader() {
		return new JdbcCursorItemReaderBuilder
				<Order>().dataSource(dataSource).
				name("jdbcItemReader").sql(select_sql).rowMapper(new OrderRowMapper<Order>()).build();
	}

	
	

}
