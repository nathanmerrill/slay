cd KoTHComm
git add .
git commit -m "Updates"
git push origin HEAD:master
cd ../
git checkout master
gradle build
java -jar build/libs/StockExchange.jar download compile
git add .
git commit -m "Updates"
git push origin master
git checkout archives
git merge master
cp build/libs/StockExchange.jar StockExchange.jar
7z a StockExchange.zip StockExchange.jar -aou
7z a StockExchange.zip submissions -aou
7z a StockExchangePlayers.zip submissions -aou
git add .
git commit -m "Updates"
git push origin archives
git checkout master