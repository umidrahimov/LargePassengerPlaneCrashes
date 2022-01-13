# LargePassengerPlaneCrashes
 
1.	Main menu
 
When system starts main menu is printed. User can enter only numbers 1-7, 0. All other inputs will be ignored. System will display proper message and prompt user to try again. This process will be repeated until expected number is entered. 

1.1.	List all entities
To list entities user should enter 1. Very short sub menu will be printed.
 
System will prompt user to enter numbers 1-3, 0. All other inputs will be ignored. System will display proper message and prompt user to try again. This process will be repeated until expected number is entered. 

1.1.1.	List all the fields of each entities
To list all the fields of each entity user should enter 1. If previously any sorting or filtering were applied then only those items will be printed. After listing every item “Total number of records listed” will be printed.
 
After printing elements system will go back and print main menu again.

1.1.2.	List only the selected fields of each entity
To list only the selected fields of each entity user should enter 2. “Please enter field names separated by comma:” message will be shown. Sample input is “date, time, operator, location”. System is not case sensitive. After listing every item “Total number of records listed” will be printed.
 
If empty string is detected system will prompt user to enter column names again. This process will be repeated until at least 1 existing column is entered. If entered column name does not exist proper message will be shown to the user.

If non-existing column name is entered system will notify the user about that with message “No existing column with name %s” and print all other fields.
 
After printing elements system will go back and print main menu again.

1.1.3.	List entities based on the range of rows
To list entities based on the range of rows user should enter 3. “Please enter range of rows separated by space. E.g: 5 100” message will be printed. Numbering is index based, starts with 0. All elements between start and end index will be printed inclusively.
 
There are many input validations there: empty input detection, non-integer value detection, wrong number of inputs detection and so on. On each error system will print relevant message. This process will be repeated until proper input is entered.
 
1.1.4.	Back to main menu
If 0 is entered system will go back and print main menu again.

1.2.	Sort all entities
To sort all entities user should enter 2 in main menu. Very short sub menu will be printed.
 
1.2.1.	Set sort conditions
To set sort conditions user should enter 1. “Please enter sort conditions. E.g: {fieldname} {ASC|DESC}” message will be printed. If only filedname is entered by user default ACS sorting will be applied. If successfully sorted “List is sorted message will be printed.” To see the results of sorting you can go to List all entities from main menu which will be automatically printed
 
There are many input validations there: empty input detection, column existence, number of inputs and so on. On each error system will print relevant message. This process will be repeated until proper input is entered. If column name is correct but sorting order is incorrect system will use default soring - ASC.
 
1.2.2.	Back to main menu
If 0 is entered system will go back and print main menu again.

1.3.	Search entities
To search entities user should enter 3 in main menu. 
 
1.3.1.	Set search conditions
To set seacrh conditions user should enter 1 in main menu. “Please enter search conditions. E.g: {fieldname} {value}” message will be printed. String fields and values are checked based on contains not exact equality. Non-string fields and values are checked based on exact equality. Expected date format is YYYY-MM-DD. Number of found entities will be printed along with the main menu.
 
There are many input validations there: empty input detection, column existence, number of inputs and so on. On each error system will print relevant message. This process will be repeated until proper input is entered.

1.3.2.	Back to main menu
If 0 is entered system will go back and print main menu again.

1.4.	List column names
To list column names user should enter 4. Column names will be printed immediately along with the main menu to proceed.
 
1.5.	Filter entities
To filter entities user should enter 5. Very short sub menu will be printed.
 
1.5.1.	Set filter conditions
To set filter conditions user should enter 1. System will prompt the user to enter the filed name:
 
There is an input validation. System will check if entered field exists. In case of failure proper message will be printed.
 
If column exists system will print sorting conditions based on type of field. All requirements of Mr. Sadili are met. Example, date field which is of type LocalDate:
 
For every sub element system will print expected value sample or description. Inside of each sub element there are many different types of validation. User will get proper error message and prompted to try again until expected input is entered.

1.5.2.	Back to main menu
If 0 is entered system will go back and print main menu again.

1.6.	Export to file
To export working sheet to CSV file user should enter 6. “Report exported successfully” message will be printed. System will automatically increment number of exported item in order to keep previous versions. If no elements in your working list “No elements to export. Reset filters and try again.” will be printed. 
If there are entities to be printed system will show message “Please enter file name without extension. If file name exists it will be overridden.”. System will check file name entered by user. Is there is prohibited character used system will print relevant message and prompt the user to try again.
 
When correct file name is entered system will print “Report exported successfully”.
 
1.7.	Reset filters
To reset filters and sorting user should enter 7. Initial list will be used from now.

1.8.	Exit
To close the system user should enter 0 in main menu.
