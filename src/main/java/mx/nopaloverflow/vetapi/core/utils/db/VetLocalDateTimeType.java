package mx.nopaloverflow.vetapi.core.utils.db;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.DateStringType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.j256.ormlite.field.SqlType.LONG;

public class VetLocalDateTimeType extends DateStringType {
    private static final VetLocalDateTimeType singleton = new VetLocalDateTimeType();

    private VetLocalDateTimeType() {
        // no classes option for singleton
        super(LONG, new Class[0]);
    }

    protected VetLocalDateTimeType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public static VetLocalDateTimeType getSingleton() {
        return singleton;
    }

    @Override
    public Object javaToSqlArg(final FieldType fieldType, final Object javaObject) {
        final var date = (LocalDate) javaObject;
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    @Override
    public Object sqlArgToJava(final FieldType fieldType,
                               final Object sqlArg,
                               final int columnPos) {
        return LocalDate.parse((String) sqlArg, DateTimeFormatter.ISO_DATE);
    }
}
