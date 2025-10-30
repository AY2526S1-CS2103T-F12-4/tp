---
layout: page
title: User Guide
---

CLInic is a **desktop app for managing patients, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, CLInic can get your patient management tasks done faster than traditional GUI apps.

![Ui](images/UI-Current.png)

## Table of Contents
- [Quick start](#quick-start)
- [Features](#features)
    - [Viewing help : `help`](#viewing-help--help)
    - [Adding a patient: `add`](#adding-a-patient-add)
    - [Listing all patients : `list`](#listing-all-patients--list)
    - [Viewing a patient : `view`](#viewing-a-patient--view)
    - [Viewing medicines taken by patient : `med`](#viewing-medicines-taken-by-patient--med)
    - [Logging a visit for a patient : `log`](#logging-a-visit-for-a-patient--log)
    - [Displaying visit dates for a patient : `display`](#displaying-visit-dates-for-a-patient--display)
    - [Editing a patient : `edit`](#editing-a-patient--edit)
    - [Locating patients by name: `find`](#locating-patients-by-name-find)
    - [Locating patients by doctor's name: `finddoc`](#locating-patients-by-doctor-finddoc)
    - [Locating patients by medicines taken: `findmed`](#locating-patients-by-medicines-taken-findmed)
    - [Deleting a patient : `delete`](#deleting-a-patient--delete)
    - [Clearing all entries : `clear`](#clearing-all-entries--clear)
    - [Exiting the program : `exit`](#exiting-the-program--exit)
    - [Saving the data](#saving-the-data)
    - [Editing the data file](#editing-the-data-file)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F12-4/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for CLInic.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar CLInic.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/UI-Current.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

  * `list` : Lists all contacts.

  * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a patient named `John Doe` to CLInic.

  * `delete 3` : Deletes the 3rd patient shown in the current list.

  * `clear` : Deletes all patients.

  * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a patient: `add`

Adds a patient to CLInic.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/DOCTOR] [t/TAG]…​ [med/MEDICINE]…​`

<div markdown="span" class="alert alert-primary">:bulb: *Tip:*
A patient can have any number of tags/medicines (including 0)
</div>

<div markdown="span" class="alert alert-primary">:bulb: *Tip:*
To create a red allergy tag, use `t/allergy` when adding a patient. Any tag containing "allergy" will appear red in the interface.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 d/James William`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/friend med/Paracetamol med/Aspirin`

### Listing all patients : `list`

Shows a list of all patients in CLInic.

Format: `list`

### Viewing a patient : `view`

Views the specified patient details from CLInic.

Format: `view INDEX`

* Views the patient details at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view 3` views the 3rd patient in the address book.
* `find Jackson` followed by `view 1` views the 1st patient in the results of the `find` command.

### Viewing medicines taken by patient : `med`

Views all medicines taken by the specified patient.

Format: `med INDEX`

* Views the medicines taken by the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `view 3` views the medicines taken by the 3rd patient in the address book.
* `find Jackson` followed by `view 1` views the medicines taken by the 1st patient in the results of the `find` command.

### Logging a visit for a patient : `log`

Logs today's date as a visit for the specified patient.

Format: `log INDEX`

* Logs a visit for the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​
* If a visit has already been logged for today, the command will fail with a message indicating it has already been logged.

Examples:
* `list` followed by `log 3` logs today's visit for the 3rd patient in the address book.
* `find Jackson` followed by `log 1` logs today's visit for the 1st patient in the results of the `find` command.

### Displaying visit dates for a patient : `display`

Displays all recorded visit dates for the specified patient.

Format: `display INDEX`

* Displays the visit dates of the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​
* If there are no recorded visits for the patient, a message indicating so will be shown.

Examples:
* `list` followed by `display 3` shows the visit dates of the 3rd patient in the address book.
* `find Jackson` followed by `display 1` shows the visit dates of the 1st patient in the results of the `find` command.

### Editing a patient : `edit`

Edits an existing patient in CLInic.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/Doctor] [med/MEDICINE]…​ [t/TAG]…​`

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags/medicines, the existing tags/medicines of the patient will be removed i.e adding of these fields is not cumulative.
* You can remove all the patient’s tags/medicines by typing `t/` or `med/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st patient to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd patient to be `Betsy Crower` and clears all existing tags.
*  `edit 2 d/` Removes the doctor assigned to that patient.

### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

### Locating patients by doctor: `finddoc`

Finds patients whose doctor's name contain any of the given keywords.

Format: `finddoc KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `william` will match all patients whose doctor is `William`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Will` will not match `William`
* Patients with doctors matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return all patients with doctor `Hans Gruber`, `Bo Yang`

Examples:
* `find Jake` returns all patients whose doctor is `jake` and `Jake Lee`.
* `find alex david` returns all patients whose doctor is `Alex Yeoh`, `David Li`<br>

### Locating patients by medicines taken: `findmed`

Format: `findmed med/MEDICINE [MORE_MEDICINES]...` or `findmed none`

* The search is case-insensitive. e.g `paracetamol` will match `Paracetamol`
* if `none` is specified after `findmed`, it returns all patients with no medicines assigned to them.
* If more than one medicine specified, it should be space-sperated like `findmed med/medA med/medB`
* The order of the keywords does not matter. e.g. `med/paracetamol med/ibuprofen` and `med/ibuprofen med/paracetamol`
  will fetch patients who take both these medicines
* Only full words will be matched e.g. `ibu` will not match `ibuprofen`
* patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `paracetamol` will return patients who take paracetamol even if they take other medicines

Examples:
* `findmed med/paracetamol`
* `findmed med/paracetamol med/ibuprofen`
* `findmed none`

### Deleting a patient : `delete`

Deletes the specified patient from CLInic.

Format: `delete INDEX`

* Deletes the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd patient in CLInic.
* `find Betsy` followed by `delete 1` deletes the 1st patient in the results of the `find` command.

### Clearing all entries : `clear`

Clears all patient entries in CLInic.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CLInic data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

CLInic data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, CLInic will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause CLInic to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous CLInic home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/Doctor] [t/TAG]…​ [med/MEDICINE]…​` <br> e.g., `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/friend med/Paracetamol med/Aspirin`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/Doctor] [t/TAG]…​ [med/MEDICINE]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find Doctor** | `finddoc KEYWORD [MORE_KEYWORDS]`<br> e.g., `finddoc Mike Ang`
**Filter by Medicines** | `findmed med/MEDICINE [med/MEDICINE]...` or `findmed none` <br> e.g., `findmed med/Paracetamol med/Ibuprofen`
**List** | `list`
**Help** | `help`
**View medicines** | `med INDEX`
**View patient** | `view INDEX`
**Log visit** | `log INDEX`<br> e.g., `log 1`
**Display visits** | `display INDEX`<br> e.g., `display 1`
