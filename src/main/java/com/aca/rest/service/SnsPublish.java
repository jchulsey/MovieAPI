package com.aca.rest.service;

import com.aca.rest.model.EmailMessage;
import com.aca.rest.model.Movie;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SnsPublish {
			
			private static final String ACCESS_KEY = "AKIA2WI55DSMSDRQNH2T";
			private static final String SECRET_KEY = "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";
			
			public static final String myTopic = "arn:aws:sns:us-east-1:735039003801:Hulsey_Movies";
			
			public static AmazonSNSClient getAwsClient() {
				//deprecated and its going away
				AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
				return snsClient;
			}
			
			public static void publishNewMovie(Movie movie) {
				PublishRequest publishRequest = new PublishRequest();
				publishRequest.setTopicArn(myTopic);
				String newMovieMessage = "title: " + movie.getTitle() + ", genre: " + movie.getGenre();
				publishRequest.setMessage(newMovieMessage);
				publishRequest.setSubject("New Movie Alert");
				
				AmazonSNSClient snsClient = getAwsClient();
				
				PublishResult publishResult = snsClient.publish(publishRequest);
				System.out.println("message id: " + publishResult.getMessageId());
			}
			
			public String sendEmail (EmailMessage message) {
				PublishRequest request = new PublishRequest();
				request.setTopicArn(myTopic);
				request.setMessage(message.getEmailText());
				request.setSubject(message.getEmailSubject());
				
				AmazonSNSClient snsClient = getAwsClient();
				PublishResult result = snsClient.publish(request);
				
				System.out.println("message id : " + result.getMessageId());
				
				return result.getMessageId();
			}
	
}
