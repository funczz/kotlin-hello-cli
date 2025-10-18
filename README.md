# kotlin-hello-cli

Kotlin Hello CLI アプリケーション

## Run
ヘルプを表示する:
```shell
./gradlew run -Pargs="-h"
```
実行コマンドの例:
```shell
./gradlew run -Pargs="-u https://example.com/foo/ -d ./bar"
```

## 補足

ステータスコードの確認
```shell
echo $?
```