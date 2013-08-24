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

The 'dcommit' command does a 'rebase', so we should force a push to git:

    $ git push origin +master:master

