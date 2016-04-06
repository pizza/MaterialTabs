#!/bin/sh
#---------------------------------------------------------------
# Given an xxxhdpi image or an App Icon (launcher), this script
# creates different dpis resources
#
# Place this script, as well as the source image, inside res
# folder and execute it passing the image filename as argument
#
# Example:
# ./drawables_dpis_creation.sh ic_launcher.png
# OR
# ./drawables_dpis_creation.sh my_cool_xxhdpi_image.png
#---------------------------------------------------------------

echo " Creating different dimensions (dips) of "$1" ..."

if [ $1 = "ic_launcher.png" ]; then
    echo "  App icon detected"

    convert ic_launcher.png -resize 192x192 drawable-xxxhdpi/ic_launcher.png
    convert ic_launcher.png -resize 144x144 drawable-xxhdpi/ic_launcher.png
    convert ic_launcher.png -resize 96x96 drawable-xhdpi/ic_launcher.png
    convert ic_launcher.png -resize 72x72 drawable-hdpi/ic_launcher.png
    convert ic_launcher.png -resize 48x48 drawable-mdpi/ic_launcher.png
#   convert ic_launcher.png -resize 36x36 drawable-ldpi/ic_launcher.png

    rm -i ic_launcher.png
else
    convert $1 -resize 75% drawable-xxhdpi/$1
    convert $1 -resize 50% drawable-xhdpi/$1
    convert $1 -resize 37.5% drawable-hdpi/$1
    convert $1 -resize 25% drawable-mdpi/$1
#   convert $1 -resize 18.75% drawable-ldpi/$1
    mv $1 drawable-xxxhdpi/$1

fi

echo " Done"
