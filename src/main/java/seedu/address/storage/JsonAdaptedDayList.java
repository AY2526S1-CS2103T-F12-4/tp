package seedu.address.storage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.DayList;

/**
 * Jackson-friendly version of {@link DayList}.
 */
class JsonAdaptedDayList {

    private final List<String> visitDates;

    /**
     * Constructs a {@code JsonAdaptedDayList} with the given visit dates.
     */
    @JsonCreator
    public JsonAdaptedDayList(@JsonProperty("visitDates") List<String> visitDates) {
        this.visitDates = visitDates;
    }

    /**
     * Converts a given {@code DayList} into this class for Jackson use.
     */
    public JsonAdaptedDayList(DayList source) {
        this.visitDates = source.getVisitDates().stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted day list object into the model's {@code DayList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day list.
     */
    public DayList toModelType() throws IllegalValueException {
        if (visitDates == null) {
            return new DayList();
        }

        try {
            List<LocalDate> modelVisitDates = visitDates.stream()
                    .map(LocalDate::parse)
                    .collect(Collectors.toList());
            return new DayList(modelVisitDates);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid date format in visit dates: " + e.getMessage());
        }
    }
}
