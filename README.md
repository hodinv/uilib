UILib
=======

Boilerplate for application with some common types of applications and some helpers

Add dependency using gradle
---------------------------


```Groovy
compile 'com.hodinv:ui-lib:0.1'
```


Main parts
-------------
 
1. [Activities templates](#ref_activities)
    * [Base content fragment holder](#ref_activities_base_content_holder)
    * [Base content fragment](#ref_activities_base_content)
    * [Progress](#ref_activities_progress)
    * [Toolbar](#ref_activities_toolbar)
    * [Left menu](#ref_activities_left_menu)
2. [Async process handling](#ref_async)
    * [Async tasks bases](#ref_async_task)
    * [RxJava based](#ref_async_rx)
3. [Helpers](#ref_helpers)
    * [Dialogs](#ref_helpers_dialogs)
    * [Enums titles](#ref_helpers_enums)
    * [Files](#ref_helpers_files)
    * [Keyboard](#ref_helpers_keyboard)
    * [Numbers](#ref_helpers_numbers)
    * [Resources](#ref_helpers_resources)
    * [TextView based switcher](#ref_helpers_textview)
4. [Logger](#ref_logger)
 
<a name="ref_activities"/>
# 1. Activities 

Main idea to have activity with one main fragment called content fragment (inherited from ContentFragment) 
and navigate form one ContentFragment to another like switching pages in browser. Other activities inherits this
behavior and add some more features. Base activity can be chosen according to your needs.


<a name="ref_activities_base_content_holder"/>
## 1.1 Base content holder

Type       | Name
-----------|----------------------
Interface  | **ContentFragmentHolder**
Class      | **ContentHolderActivity**



Interface ContentFragmentHolder has all features of all stack of template activities. If some functions are not applyable
to selected base activity they will do nothing.
Base content holder class ContentHolderActivity implements next functionality:

- creates and setup UI. By default is used simple layout where content matches parent size.
- change fragment in fragment container (functions startFragment and startFragmentWithStacking)
- process back pressing
- ability to create exit confirmation 

Functions to inherit:

- To set own layout: getDefaultLayout and setupUI (to setup links to your custom controls). Your layout should have container for content fragment with id=lyt_content.
- To process exit confirmation logic : canExitImmediate for check if we need confirmation, confirmExit to process confirmation
and do call exit stuff if confirmed 
- do some stuff on exit: onExit

<a name="ref_activities_base_content"/>
## 1.2 Base content fragment

Type       | Name
-----------|----------------------
Class      | **ContentFragment**


Class ContentFragment holds reference to content holder activity and can be used only inside such activity. 
Has mechanism to do stuff with base activity in safe mode (NPE safe)
Example for set title. checkRef will check of base activty is still presend and attached and if ok will run code 
in doAction passing content fragment holder interface

```JAVA
checkRef(new IfHasActivity() {
    @Override
    public void doAction(@NonNull ContentFragmentHolder holder) {
        holder.setTitle(title);
    }
});
```

All ContentFragmentHolder methods are wrapped in such manner and delegated to activity. So you can safly call them and will not get NPE.
However you should run UI function in UI thread.

Other methods to override to change behavior:

Method                           | Info
---------------------------------|---------------------------------------------------------------------------------
boolean showToolbar()            | for activity with toolbar. if false - will hide toolbar 
boolean onBack(boolean soft)     | if back pressed here you can process it and consime (do not do default logic)
String getTitle()                | title that is dynamicaly changed - has more priority than getDefaultTitle    
int getDefaultTitle()            | defult title string resource                                                 
int getMenuId()                  | for activity with left menu. current menu to show selected                   
boolean hasLeftMenu()            | for activity with left menu. if false will hide menu and show back arrow     
ArgumentsBuilder arguments()     |  simple builder to pass argument to frgamnet (just helper for setArguments)  

<a name="ref_activities_progress"/>
## 1.3 Progress

Type       | Name
-----------|-------------------------------
Class      | **ProgressContentHolderActivity**

Activity ProgressContentHolderActivity inherits all from ContentFragmentHolderActivty and adds progress functionality
View from getDefaultLayout should have ProgressBar with id = lyt_progress and TextView for progress title with id = txt_progress_title
By default progress overlaps all functionality and in next activities 
(toolbar activity - overlaps toolbar, in left menu activity - hides left menu and disables it)

Methods                         | Info
--------------------------------|-----------------------------------------------------------------------------------------
void showProgress               | Show progress without title
void hideProgress               | Hides progress (for left menu activity if menu was enabled before progress - enables it)
void showProgress(int titleId)  | show progress with title from string resource
void showProgress(String title) | show progress with title 

<a name="ref_activities_toolbar"/>
## 1.4 Toolbar

Type       | Name
-----------|-------------------------------
Class      | **ToolbarContentHolderActivity**

Activity with toolbar - ToolbarContentHolderActivity. Layout id for toolbar = lyt_toolbar
It uses showToolbar method from content fragment to show/hide toolbar (for support fullscreen activities)
Also as it uses toolbar we need to switch of standard action bar - adding for ex. style without toolbar for activity
Without it we will get runtime error on start

```XML
<application           
        android:theme="@style/AppTheme">
        <!-- ..... -->
        </application>
```

and defining theme as 

```XML
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="actionBarTheme">@style/ThemeOverlay.AppCompat.Dark.ActionBar</item>
    </style>
```
Here how application layout looks for toolbar activity

```XML
 <!-- some xml here -->
 <android.support.v7.widget.Toolbar
             android:id="@+id/lyt_toolbar"
             android:layout_width="match_parent"
             android:layout_height="?attr/actionBarSize"
             android:background="?attr/colorPrimary"
             app:layout_collapseMode="pin"
             app:theme="?attr/actionBarTheme"/>
 <!-- some xml there -->
```
we need to set proper theme usage for toolbar because if we not do this we will have wrong colors (as they defined in NoActionBar theme

<a name="ref_activities_left_menu"/>
## 1.5 Left menu

Type       | Name
-----------|-------------------------------
Class      | **LeftMenuContentHolderActivity**

Adds left menu with drawer to tollbar activity. For styling see [Toolbar](#ref_activities_toolbar) - you will need to remove standard action bar in order to use it
Activity uses fragment's method hasLeftMenu to enable/disable left menu (in disabled mode replaced with back arrow treated  as soft back)
Also uses fragment method getMenuId to show one of menu items as current (sets selected state)
Showing progress will disable menu untill progress is hide. Than previouse state (enabled/disabled) will be restored.

You will need to define some methods:

Method                           | Info
---------------------------------|-------------------------------------------------------------------------------------
public int[] getMenuIds()        | array of ids of items in menu processed as menu items (can be pressed and selected)
int getMenuLayout()              | layout resource to use as menu (will be inflated into menu area)

Also you can use some methods (they are already called from content fargment)

Method                                | Info
--------------------------------------|----------------------------------------------------
void toggleMenu()                     | Toggle menu to show/hide
void setMenuEnabled(boolean enabled)  | Sets menu mode or back arrow mode (disabled menu)

<a name="ref_async"/>
# 2. Async process handling
This classes are based on content fragments (and can be used on content holders activities) and added some
logic for running processes

<a name="ref_async_task"/>
## 2.1 Async tasks bases

Type       | Name
-----------|-------------------------------
Interface  | **FutureCallback**
Class      | **ControlledAsyncTask**
Class      | **AsyncTasksContentFragment**



This classes based on AsyncTask. Log is to run async task while fragment is not stopped. In onStop all running tasks will be canceled.
AsyncTask should be child of ControlledAsyncK=Task with logic of adding and removing from list of running tasks of fragment
 
Method to override in ControlledAsyncTask:

Method                                | Info
--------------------------------------|-----------------------------------
Result doInBackground()               | return result or throws exception

Future to process result or error is passed in constructor. Future methods will be called from UI thread.

Method to start task in AsyncTaskContentFragment

Method                                                | Info
------------------------------------------------------|---------------------------------------------------------------------------------
void startTask(final ControlledAsyncTask taskToStart) | Starts task and adds it to lasks list. Also pass self to task as holder


<a name="ref_async_rx"/>
## 2.3 RxJava based

Type       | Name
-----------|-------------------------------
Class      | **RxContentFragment**

RxContentFragment adds logic to connect RxJava observable with ContentFragment lifecycle. 
If subscription was "add" it will be connected to lifecycle. If fargment will be stopped subscriptions will be unsubscribed so callback will not occure.
If fragment will be started once more - subscriptions will be renewed and data will produced.
If subscription was "run" - it will be only run in proper thread - no connection to lifecycle, just helper

Methods that can be used to add/run subscription

Method                                                                                              | Info
----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------
<T> void addSubscription(Observable.OnSubscribe<T> subscription, final Observer<? super T> action)  | add observable onsubscribe interface to fragments lifecycle and connects action to it
<T> void addSubscription(Observable<T> observable, final Observer<? super T> action)                | add observable to fragments lifecycle and connects action to it
void runSubscription(Observable<?> observable)                                                      | helper to just run observable in proper threads

<a name="ref_helpers"/>
# 3. Helpers

Here some helpers for common routines.

<a name="ref_helpers_dialogs"/>
## 3.1 Dialogs

Type       | Name
-----------|-------------------------------
Class      | **DialogHelper**

Helper for creating some tandard dialogs

Method                                                                              | Info
------------------------------------------------------------------------------------|---------------------------------------------------------------------------------
AlertDialog showAlert(Context context, String title, String message)                | Just alert with message
void showAlert(Context context, String title, String message, final Runnable onOk)  | Alert with message and action on OK pressed
<T> void showSelect(Context context, String title, Map<T, String> values, T current, final OnSelect<T> callback) | shows dialog with items to select (map with item and title) and if slected pass it to OnSelect callback
void askConfirm(Context context, String title, String message, String okLabel, final Runnable onOk) | Dialog to confirm action. With message, title, label for confirm action and runnable to do if confirmed



<a name="ref_helpers_enums"/>
## 3.2 Enums titles

Type       | Name
-----------|-------------------------------
Class      | **EnumsHelper**

Helper to manage titles for enums.
Here example for some enums

```JAVA
public enum Demo1 {
    VALUE1, VALUE2
}


public enum Demo2 {
    VALUE3, VALUE4
}

public enum Demo3 {
    VALUE4, VALUE5
}
```

Define string resources for enums. Here demo with prefix and with prefix and suffix and without both:

```XML
<!-- titles for Demo1 -->
<string name="str_demo1_value1">First value</string>
<string name="str_demo1_value2">Second value</string>

<!-- titles for Demo2 -->
<string name="str_demo2_value3_label">First value</string>
<string name="str_demo2_value4_label">Second value</string>

<!-- titles for Demo3 -->
<string name="demo3_value5">First value</string>
<string name="demo3_value6">Second value</string>
```


Then init EnumsHelper (better in Application onCreate)

```JAVA
@Override
public void onCreate() {
    super.onCreate();
    EnumsHelper.init(this);
    EnumsHelper.getInstance().loadEnum(Demo1.class, "str", null)
    EnumsHelper.getInstance().loadEnum(Demo2.class, "str", "label")
    EnumsHelper.getInstance().loadEnum(Demo3.class);
}

```

And here is demo for usage
 
```JAVA
String title = EnumsHelper.getInstance().getTitle(Demo1.VALUE1);
```

<a name="ref_helpers_files"/>
## 3.3 Files

Type       | Name
-----------|-------------------------------
Class      | **FilesHelper**

This helper for creating files in data directory with processing cases where getExternalFilesDir returns null.
Steps are next
* First try to get directory from context method getExternalFilesDir
* If got null - check if extarnal storage present (Environment.getExternalStorageDirectory())
* If no - throws RuntimeException
* If has - target directory will be  Environment.getExternalStorageDirectory() + "/Android/data/" + package name + type (if not null)
* Then check and creates directory if still not exists


Method                                                                   | Info
-------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------
File getDataDir(Context context, @Nullable String fileType)              | Creates directory in standard place for application by type
File getDataDir(Context context)                                         | Creates directory in standard place for application
File getFileByName(Context context, String fileName, String fileType)    | Returns file (not created) in directory (created if not existed) in standart place for application by type
File getFileByName(Context context, String fileName)                     | Returns file (not created) in directory (created if not existed) in standart place for application


<a name="ref_helpers_keyboard"/>
## 3.4 Keyboard

Type       | Name
-----------|-------------------------------
Class      | **KeyboardHelper**

Just method to force hide of keyboard

<a name="ref_helpers_numbers"/>
## 3.5 Numbers

Type       | Name
-----------|-------------------------------
Class      | **NumbersHelper**

Here methods to parse double values that can be separated by comma or dot

<a name="ref_helpers_resources"/>
## 3.6 Resources

Type       | Name
-----------|-------------------------------
Class      | **ResourcesHelper**

Helper to get all resource values from R class filtering by name

Example for getting all ids that names startes from "menu_"

```JAVA
@Override
public int[] getMenuIds() {
    return ResourcesHelper.loadResourcesIds(R.id.class, new ResourcesHelper.Filter() {
        @Override
        public boolean match(String name) {
            return name.startsWith("menu_");
        }
    });
}
```

<a name="ref_helpers_textview"/>
## 3.7 TextView based switcher

Type       | Name
-----------|-------------------------------
Class      | **TextViewSwitcher**

Adds similar to RadioGroup/RadioButtons behavior to set of text views.

If view is clicked you will will get its pos in callback (from 0 to count-1)
Clicked items gots state selected. For others such state is cleared.


<a name="ref_logger"/>
# 4. Logger 

Type       | Name
-----------|-------------------------------
Class      | **Log**

Wraps common methods (d/w/i/v/e) for standard Andorid Log class with adding functionality to save logging to file

You need to init log in Application.onCreate in order to use it. If you want to setup thrsholds (size limits etc.) you should do it before initializing.

Logs are saved in external storage

Setup methods:

Method                                                        | Default value | Info
--------------------------------------------------------------|---------------|----------------------------------------------
void setMinimalStorageFreeSpaceToRunLogging(long sizeInBytes) | 100Mb         | Minimal free space on SD for logging to start
void setLoggerFolder(String loggerFolder)                     | logger        | folder in /Android/data/package/ to save logs
void setMaxSizeOfLogFile(int maxSizeOfLogFile)                | 1Mb           | Maximum size for one log file
void setNumberOfLogFiles(int numberOfLogFiles)                | 30            | Number of files to save 

Other methods:

Method                                                                        |  Info
------------------------------------------------------------------------------|----------------------
void initLogger(int level, int fileLevel, boolean logToFile, Context context) | init logging
void v(String TAG, String msg)                                                | log verbose message
void d(String TAG, String msg)                                                | log debug message
void i(String TAG, String msg)                                                | log info message
void w(String TAG, String msg)                                                | log warning message
void e(String TAG, String msg)                                                | log error message


