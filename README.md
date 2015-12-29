UILib
=======

Boilerplate for application with some common types of applications and some helpers

Main parts
-------------
 
1. [Activities templates](#ref_activities)
    * [Base content fragment holder](#ref_activities_base_content_holder)
    * [Base content fragment](#ref_activities_base_content)
    * [Progress](#ref_activities_progress)
    * Toolbar
    * Left menu
2. Async process handling
    * Async tasks bases
    * RxJava based
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





















....
(end)