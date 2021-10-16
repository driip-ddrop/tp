## User Guide
contHACKS is a **desktop app for managing contacts, optimized for use via a Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). contHACKS streamlines the creation, reading, updating and deleting operations of the address book to make it fast and efficient for Teaching Assistants, easing their workload to focus on the more important task - teaching.

## Table of Contents
* [Quick start](#quick-start)

* [Features](#features)

   * Help page: [help / man](#help)

   * Adding a contact: [add](#add)

   * Listing all contacts: [list / ls](#list)

   * Find contacts by name / tag: [find](#find)

   * Edit contact: [edit / update](#edit)

   * Delete contact individually / in batches: [delete / del / rm](#delete)

   * Clear all contacts: [clear / clr](#clear)

   * Add description to contact: [remark](#remark)

   * Tag a contact: [tag](#tag)

   * Add contact to favourite: [fav](#fav)

   * Exiting the app: [exit / quit](#exit)

* [Saving the data](#saving-data)

* [Editing the data file](#editing-data)

* [Command Summary](#summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start <a name="quick-start"></a>

1. Ensure you have Java `11` or above installed in your computer.

1. Download the latest `contHACKS.jar` from here.

1. Copy the file to the folder you want to use as the _home folder_.

1. Double-click the file to start the app. A GUI should appear in a few seconds.

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`** : `add n/Ben p/91234567 e/ben123@gmail.com m/CS2103T` : Adds a contact named `Ben`.

   * **`delete 3`** : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features <a name="features"></a>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/{NAME}`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/{NAME} [t/{TAG}]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/{NAME} p/{PHONE}`, `p/{PHONE} n/{NAME}` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

***

### Viewing help : `help` <a name="help"></a>

Shows a message explaining how to access the help page.

Format: `help`

Command aliases: `man`

***

### Adding a person: `add` <a name="add"></a>

Adds a person to the address book. Name, email and module code are **compulsory**. Phone number, telegram handle and tags are **optional**. Parameters can be in any order.

Format: `add n/{NAME} e/{EMAIL} m/{MODULE_CODE} [p/{PHONE}] [h/{TELEGRAM_HANDLE}] [t/{TAG}]`

Examples:
* `add n/Ben e/ben123@gmail.com m/CS2103T h/@BenIsHere t/Overseas`
* `add n/Mary p/98765432 e/mary123@gmail.com m/CS2100`

***

### Listing all persons : `list` <a name="list"></a>

Shows a list of all persons in the address book.

Format: `list`

Command aliases: `ls`

***

### Find contacts by name / tag: `find` <a name="find"></a>

Finds a name / tag.
* The search is case-insensitive. eg hans will match Hans
* The order of the keywords does not matter. eg. Hans Bo will match Bo Hans
* Only the name/tag is searched.
* Only full words will be matched eg. Han will not match Hans
* Persons matching at least one name/tag will be returned.

Format: `find {NAME}`/`find {TAG}`

Examples:
* `find Ben`
* `find CS2103T`

***

### Edit contact: `edit` <a name="edit"></a>

Updates the information of a contact.

Edits the person at the specified index.
* The index refers to the index number shown in the displayed person list.
* The index number must be a positive integer 1,2,3…
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Format: `edit {INDEX} [n/{NAME}] [e/{EMAIL}] [m/{MODULE_CODE}] [t/{TAG}] [p/{PHONE}] [h/{TELEGRAM_HANDLE}]`

Examples:
* `edit 1 p/91234567 e/ben321@gmail.com` Edits the phone number and email address of the 1st person to be `91234567` and `ben321@gmail.com` respectively.
* `edit 2 n/John Doe` Edits the name of the 2nd person to be `John Doe` and clears all existing tags.
* `edit 3 h/@BenWasHere t/Overseas` Edits the telegram handle of the 3rd person to be `@BenWasHere` and adds an `Overseas` tag.

Command aliases: `update`

***

### Delete contact individually / in batches: `delete` <a name="delete"></a>

Delete the specified contact(s) from the address book. It can also be used to delete all contacts associated with a tag (using `b/{TAG}`).

* Deletes the person at the specified index (inclusive).
* Index refers to the index number shown in the displayed person list.
* The index must be a positive integer 1,2,3...
* `INDEX_B` should be a positive integer strictly greater than `INDEX_A`.

Format: `delete {INDEX}`/ `delete {INDEX_A, INDEX_B}` / `delete b/{TAG}`

Examples:
* `delete 2` deletes the 2nd contact.
* `delete 2, 5` deletes the 2nd, 3rd, 4th and 5th contacts.
* `delete b/CS2103T` deletes all the contacts from CS2103T.

Command aliases: `del` `del` `rm` 

***

### Delete all contacts: `clear` <a name="clear"></a>

Purges **all** existing contacts from the address book. Use with caution.

Format: `clear` `clr`

***

### Add description to contact: `remark` <a name="remark"></a>

Adds a description that will be tagged to the contact.

Format: `remark {INDEX} {DESCRIPTION}`

Examples:
* `remark 1 punctual`
* `remark 2 currently overseas`
***

### Tag a contact: `tag` <a name="tag"></a>

Tags a contact with a category.

Format: `tag {INDEX} {TAG}`

Examples:
* `tag Ben CS2103T`
* `tag Mary Overseas`

***

### Exiting the program : `exit` <a name="exit"></a>

Exits the program.

Format: `exit`

Command aliases: `quit`

***

### Saving the data <a name="saving-data"></a>

Contact data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file <a name="editing-data"></a>

contHACKS data are saved as a JSON file `[JAR file location]/data/contHACKS.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, contHACKS will discard all data and start with an empty data file at the next run.
</div>


## Command Summary <a name="summary"></a>

| Command           | Format                                                                                                    | Example                   |
|-------------------|-----------------------------------------------------------------------------------------------------------| --------------------------|
| help / man        | `help`                                                                                                    | `help`                    |
| add               | `add n/{NAME} e/{EMAIL} m/{MODULE_CODE} [p/{PHONE}] [h/{TELEGRAM_HANDLE}] [t/{TAG}]`                      | `add n/Ben Davies e/ben123@gmail.com m/cs2103t`|
| list / ls         | `list`                                                                                                    | `list`                    |
| find              | `find {NAME}`/`find {TAG}`                                                                                | `find Ben`/`find CS2103T` |
| edit / update     | `edit {INDEX} [n/{NAME}] [e/{EMAIL}] [m/{MODULE_CODE}] [p/{PHONE}] [h/{TELEGRAM_HANDLE}] [t/{TAG}]`       | `edit 1 p/91234567 e/ben321@gmail.com`|
| delete / del / rm | `delete {INDEX}`/`delete {INDEX_A}, {INDEX_B}`/`delete b/{TAG}`                                           | `delete 2`/`delete 2, 5`/`delete b/CS2103T`|
| clear / clr       | `clear`                                                                                                   | `clear`                    |
| remark            | `remark {INDEX} {DESCRIPTION}`                                                                            | `remark 2 absent`          |
| tag               | `tag {INDEX} {TAG}`                                                                                       | `tag 2 overseas`           |
| exit / quit       | `exit`                                                                                                    | `exit`                     |
