UILib
=====

Boilerplate for application with some common types of applications and some helpers

Main parts
----------
 
1. [Activities templates](#ref_activities)
    * Base content fragment holder
    * Base content fragment
    * Progress
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

### 1. Activities 

Main idea to have activity with one main fragment called content fragment (inherited from ContentFragment) 
and navigate form one ContentFragment to another like switching pages in browser. Other activities inherits this
behavior and add some more features. Base activity can be chosen according to your needs.

## 1.2 Base content holder

Interface ContentFragmentHolder has all features of all stack of template activities. If some functions are not applyable
to selcted base activity they will do nothing.
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




























....
(end)