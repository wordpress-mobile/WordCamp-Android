#!/bin/sh

LANG_FILE=tools/exported-language-codes.csv
RESDIR=app/src/main/res/
URL=https://translate.wordpress.org/projects/apps/wordcamp-android/dev

for line in $(grep -v en-rUS $LANG_FILE) ; do
    code=$(echo $line|cut -d "," -f1|tr -d " ")
    local=$(echo $line|cut -d "," -f2|tr -d " ")
    echo updating $local - $code
    test -d $RESDIR/values-$local/ || mkdir $RESDIR/values-$local/
    test -f $RESDIR/values-$local/strings.xml && cp $RESDIR/values-$local/strings.xml $RESDIR/values-$local/strings.xml.bak
    curl -sSfL --globoff -o $RESDIR/values-$local/strings.xml "$URL/$code/default/export-translations?filters[status]=current&format=android" || (echo Error downloading $code && rm -rf $RESDIR/values-$local/)
    test -f $RESDIR/values-$local/strings.xml.bak && rm $RESDIR/values-$local/strings.xml.bak
done
