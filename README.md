# CodeComments #

CodeComments - JetBrains products plugin for add comments to code without change it.

### Overview ###

###### Add new tool window panel `Code comment`. ######
- Select `Project` tab for show all comments in project. Select `Current File` tab for show only current file comments.
- Select text in editor and click button `Add comment` for adding comment to selected code.
- Select item in list of comments and click button `Remove comments` for remove comment.
- Click button `Refresh comments` for update comments list.
- Click button `Download comments` for export all comments to file (use [freemarker](http://freemarker.org) templates, see [data model](#markdown-header-data-model)).
- For buttons exists keymap settings.
- Double click on list item for show commented code in IDE editor window.
###### Comments stores for project (work only for directory-based project format). ######
###### Add inspection `Code comments -> Show all comments` for highlight all comments to code. ######

#### Data model ####

```
FIELD                       TYPE            DESCRIPTION
---                         ---             ---
fileComments                {collection}    all comments grouped by files
    fileInformation         {object}        information about file
        path                {string}        file path
    comments                {collection}    all comments for file
        fileInformation     {object}        information about file
            path            {string}        file path
        codeInformation     {object}        commented code information
            codeText        {string}        text of commented code
            start           {int}           start position in file
            end             {int}           end position in file
        commentHistory      {object}        history of comments
            records         {collection}    comments
                text        {string}        comment text
```

### JetBrains Repository ###

You can install plugin from repository:
```
Preferences/Settings->Plugins->Browse repositories...
```
 Then type in search:
```
Code comments
```

### Download & Installation ###

All available versions of plugin are in releases You can [download](https://bitbucket.org/indulgent/codecomments/downloads) zip file from latest release and install it manually in:

```
Preferences/Settings->Plugins->Install plugin from disk
```

### Change notes ###
#### Version 2.2-SNAPSHOT ####
- Use gradle for build.
#### Version 2.1 ####
- Add "Download comments" button for export all comments to file.
#### Version 2.0 ####
- Use IntelliJ Platform UI components.
- Add multiline field in comment input dialog.
- Add filters "Project" and "Current File".
- Add grouping comments by file.
- Add keymap settings for add, remove and refresh comments "Settings -> Keymap -> Main menu -> Code -> Add comment/Remove comments/Refresh comments".
- Add icon to button.
#### Version 1.1 ####
- Add inspection "Code comments -> Show all comments"
#### Version 1.0 ####
- Plugin add new tool window panel "Code comment"
- Select text in editor and click button add ("+") for adding comment to selected code.
- Select item in list of comments and click button remove ("-") for remove comment.
- Click button refresh ("*") for update comments list.
- Double click on list item for show commented code in IDE editor window.
- Comments stores for project (work only for directory-based project format).

### Author ###
[indulgent](mailto:indulgent@inbox.ru) (indulgent@inbox.ru)
