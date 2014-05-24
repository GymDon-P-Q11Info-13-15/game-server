rm -R "Game Server/bin"
mkdir "Game Server/bin"
CLASSPATH="src/"
for entry in $(cat CLASSPATH)
do
	CLASSPATH=$CLASSPATH:lib/$entry
done
cd "Game Server"
echo Classpath: $CLASSPATH
echo Compiling...
javac -classpath "${CLASSPATH}" -d bin/ src/de/gymdon/inf1315/game/server/Server.java
cd bin
for entry in $(cat ../../CLASSPATH)
do
	echo Extracting $entry...
	jar xf ../lib/$entry
done
rm -r META-INF
echo Including res/
cp -r ../res/* .
echo Jarring to bin/server.jar
jar cfe server.jar de.gymdon.inf1315.game.server.Server .
cd ../..
