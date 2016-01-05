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
3. Helpers
    * dialogs
    * enums titles
    * files
    * keyboard
    * numbers
    * resources
    * TextView based switcher
 
<a name="ref_activities"/>

# 1. Activities 

Main idea to have activity with one main fragment called content fragment (inherited from ContentFragment) 
and navigate form one ContentFragment to another like switching pages in browser. Other activities inherits this
behavior and add some more features. Base activity can be chosen according to your needs.


<a name="ref_activities_base_content_holder"/>
## 1.1 Base content holder

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