package com.mgmtp.radio.domain.station;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mgmtp.radio.sdo.SongStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "song")
public class Song {
	@Id
	private String id;
	private String songId;
	private String source;
	private SongStatus status;
	private boolean skipped;
	private String url;
	private String title;
	private String thumbnail;
	private long duration;
	private String creatorId;
	private List<String> upVoteUserIdList = Collections.emptyList();
	private List<String> downVoteUserIdList = Collections.emptyList();
	private String message;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate createdAt;
}
