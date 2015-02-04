package com.example.renan.english.util;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

/**
 * Created by renan on 12/12/14.
 */
public class CustomDateFormat extends DateFormat {

    private static final long serialVersionUID = 1L;

    @Override
    public Date parse(String source, ParsePosition pos) {
        pos.setIndex(source.length());
        return ISODateTimeFormat.dateTime().parseDateTime(source).toDate();
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return toAppendTo.append(ISODateTimeFormat.dateTime().print(new DateTime(date).withZone(DateTimeZone.UTC)));
    }

    @Override
    public Object clone() {
        return this;
    }

}