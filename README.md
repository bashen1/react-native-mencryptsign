# react-native-mencryptsign

[![npm version](https://badge.fury.io/js/react-native-mencryptsign.svg)](https://badge.fury.io/js/react-native-mencryptsign)

## 开始

`$ npm install react-native-mencryptsign --save`

### iOS

```sh
cd ios
pod install
```

## 使用

```javascript
import * as mEncryptSign from 'react-native-mencryptsign';
let newSign = await mEncryptSign.makeSign({
  secret: 'aaaaaasdddddddd',
  params: {
    type: 'value' // value 只能是 string 与 int
  },
});

```
