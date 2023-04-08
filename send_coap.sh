#!/bin/bash

usage() {
	echo "usage $0 [-h] -m method -u uri"
    echo "example: $0 -u coap://127.0.0.1:5683/alarm"
	exit 1
}

METHOD="get"
URI=""

while getopts "m:u:h" o; do
    case "$o" in
        m)  METHOD="$OPTARG"
            ;;
        u)  URI="$OPTARG"
            ;;
        h)  usage
            ;;
        *)  usage
            ;;
    esac
done

# Shift parameters away. In this case, useless.
shift $(expr $OPTIND - 1)

if [ ! -z "$URI" ]; then
    /bin/bash -c "coap-client -m $METHOD $URI"
else
    usage
fi