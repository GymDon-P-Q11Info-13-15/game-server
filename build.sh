rm -R "Game Server/bin"
mkdir "Game Server/bin"
CLASSPATH="src/"
for entry in $(cat CLASSPATH)
do
	CLASSPATH=$CLASSPATH:lib/$entry
done
cd "Game Server"
echo Classpath: $CLASSPATH
javac -classpath "${CLASSPATH}" -d bin/ src/de/gymdon/inf1315/game/server/Server.java
cd ..
