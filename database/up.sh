#!/usr/bin/env bash
s=$BASH_SOURCE ; s=$(dirname "$s") ; s=$(cd "$s" && pwd) ; SCRIPT_HOME="$s"  # get SCRIPT_HOME=executed script's path, containing folder, cd & pwd to get container path
[ -f "$SCRIPT_HOME/.env" ] && export $(grep -v '^#' "$SCRIPT_HOME/.env" | xargs)

cat "$SCRIPT_HOME/.env"
echo "
docker compose -f "$SCRIPT_HOME/mysql.yml" up -d --force-recreate
"
# run the container
docker compose -f "$SCRIPT_HOME/mysql.yml" up -d --force-recreate  # ref. https://forums.docker.com/t/named-volume-with-postgresql-doesnt-keep-databases-data/7434/2
docker ps
docker logs $CONTAINER_NAME
# aftermath note
echo "
# after container run, we can use 'mysql' via
docker exec -it $CONTAINER_NAME mysql -h 127.0.0.1 -u $MYSQL_USER -p$ROOT_PASSWORD

# or first step 1/2, open bash prompt first
docker exec -it $CONTAINER_NAME bash #ref. https://askubuntu.com/a/507009/22308
# then step 2/2, run psql
mysql -u $MYSQL_USER -P 3306 -p

# if you don't have local mysql command, then make this alias
echo \"
# create mysql command
alias mysql='docker exec -it $CONTAINER_NAME mysql -u $MYSQL_USER -p$ROOT_PASSWORD'
\" >> $HOME/.bashrc
mysql --version  # aftermath check
"