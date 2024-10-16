#!/usr/bin/env bash
s=$BASH_SOURCE ; s=$(dirname "$s") ; s=$(cd "$s" && pwd) ; SCRIPT_HOME="$s"  # get SCRIPT_HOME=executed script's path, containing folder, cd & pwd to get container path
[ -f "$SCRIPT_HOME/.env" ] && export $(grep -v '^#' "$SCRIPT_HOME/.env" | xargs)
echo "stop $CONTAINER_NAME"

docker stop $CONTAINER_NAME && docker rm $CONTAINER_NAME
