package org.innopolis.kuzymvas.secondexam.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class ReportDAO {
    Integer id;
    String name;
    Date submissionDate;
    String text;
    Boolean approved;
    Integer submittedBy;
}
