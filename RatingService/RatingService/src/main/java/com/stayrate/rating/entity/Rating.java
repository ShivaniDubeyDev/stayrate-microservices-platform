package com.stayrate.rating.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("micro_user_ratings")
public class Rating {

	@Id
	@Field("rating_id")
	private String ratingId;

	@Field("user_id")
	private String userId;

	@Field("hotel_id")
	private String hotelId;

	private int rating;
	private String feedback;
}