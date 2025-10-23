package seedu.address.model.visit;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a list of days a patient has visited the clinic.
 * Guarantees: immutable; visit dates are valid and not null.
 */
public class DayList {

    private final List<LocalDate> visitDates;

    /**
     * Constructs an empty DayList.
     */
    public DayList() {
        this.visitDates = new ArrayList<>();
    }

    /**
     * Constructs a DayList with the given visit dates.
     *
     * @param visitDates A list of visit dates.
     */
    public DayList(List<LocalDate> visitDates) {
        requireNonNull(visitDates);
        this.visitDates = new ArrayList<>(visitDates);
        Collections.sort(this.visitDates);
    }

    /**
     * Adds a visit date to the list.
     *
     * @param visitDate The date to add.
     */
    public DayList addVisitDate(LocalDate visitDate) {
        requireNonNull(visitDate);
        List<LocalDate> newVisitDates = new ArrayList<>(this.visitDates);
        if (!newVisitDates.contains(visitDate)) {
            newVisitDates.add(visitDate);
            Collections.sort(newVisitDates);
        }
        return new DayList(newVisitDates);
    }

    /**
     * Removes a visit date from the list.
     *
     * @param visitDate The date to remove.
     */
    public DayList removeVisitDate(LocalDate visitDate) {
        requireNonNull(visitDate);
        List<LocalDate> newVisitDates = new ArrayList<>(this.visitDates);
        newVisitDates.remove(visitDate);
        return new DayList(newVisitDates);
    }

    /**
     * Returns an immutable view of the visit dates list.
     */
    public List<LocalDate> getVisitDates() {
        return Collections.unmodifiableList(visitDates);
    }

    /**
     * Returns true if the given date is in the visit list.
     */
    public boolean hasVisitDate(LocalDate visitDate) {
        requireNonNull(visitDate);
        return visitDates.contains(visitDate);
    }

    /**
     * Returns the number of visits.
     */
    public int getVisitCount() {
        return visitDates.size();
    }

    /**
     * Returns the most recent visit date, or null if no visits.
     */
    public LocalDate getMostRecentVisit() {
        return visitDates.isEmpty() ? null : visitDates.get(visitDates.size() - 1);
    }

    /**
     * Returns the earliest visit date, or null if no visits.
     */
    public LocalDate getEarliestVisit() {
        return visitDates.isEmpty() ? null : visitDates.get(0);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DayList)) {
            return false;
        }

        DayList otherDayList = (DayList) other;
        return visitDates.equals(otherDayList.visitDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitDates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("visitDates", visitDates)
                .toString();
    }
}
