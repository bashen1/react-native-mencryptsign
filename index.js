import {NativeModules} from 'react-native';

const {RNReactNativeMencryptSign} = NativeModules;

export async function makeSign(params) {
    return await RNReactNativeMencryptSign.makeSign(params);
}
