Trabajo práctico para la materia TDAM@UTN
-----------------------------------------

## SVN <-> GIT

To see what is going to be committed one can choose the following options.

    $ gitk git-svn..
    $ gitk
    $ git log remotes/git-svn.. --oneline
    $ git svn dcommit --dry-run

To really commit to SVN:

    $ git svn dcommit

AFTER the 'dcommit', push the changes go Git (this way te rebase made by dcommit doesn't breaks history):

    $ git push origin

## Login to SVN

The password to use is shown at https://code.google.com/hosting/settings

