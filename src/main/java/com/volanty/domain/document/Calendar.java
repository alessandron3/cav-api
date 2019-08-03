package com.volanty.domain.document;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document
@JsonIgnoreProperties({"id"})
public class Calendar {

    @Id
    private String id;
    private String date;
    private List<CavCalendar> cavs;


}
