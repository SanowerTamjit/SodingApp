# SodingApp
This is a Simple android mobile app using Java, SQLite, XML.
The Database Architecture:

Database name: soding.db
Table Name: task
Columns: id --- VARCHAR (7) PK,
 	   name – TEXT,
       description – TEXT,
	   dateCreated – DATE,
	   dateUpdated – DATE.

There is a 2 Activity & 1 Alert Dialog. First one is Main Activity, 2nd Activity is TaskSave Activity & The alert dialog is for update/edit information.

In this Main activity I show the all data of task in recycleView. There are two icons In the toolbar. First on is for Open Save New Task Activity & 2nd one is for refresh data.

1st Icon: After Tapping 1st Icon(Save) the TaskSave Activity will open. 
In this activity u need to put only Name & Description Value. Don’t need to put Date & ID. Because the Date is automatic taken & The ID auto generated.

There is two button in this activity. Buttons Are Save & Cancel.
Save Button: By Tapping Save Button. The data will inserted & show this message on Toast(Bottom). If not inserted the warning message will show on Toast(Bottom). 
Cancel Button: By Tapping Cancel Button, you can back out from this activity.

Now come to again first activity(HomePage).
In recycleView you Can see all Task which you save already. 

The recycleView elements are. ID(position: Left-Top), Name(position: Left-Bottom), UpdatedDate(Position: Right-Top) and DeleteButton(Position: Right-Bottom).
Delete Button: By Tapping this button you can delete a data from a list which data you want to delete.
Refresh Icon: After Saving, Updating & Deleting data you need to tap on this icon for reload you updated data.

By Clicking a row from the list, you can see a window comes.
In this window, you can see the ID, CreatedDate, Name & Description.
You can update your data by provide Name or Description Value after tapping UpdateButton.

That’s it.   

