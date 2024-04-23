import {NativeModules} from 'react-native';

const {RNReactNativeMencryptSign} = NativeModules;

export async function makeSign(params) {
    return await RNReactNativeMencryptSign.makeSign(params);
}

export async function makeDeviceInfo(params) {
    return await RNReactNativeMencryptSign.makeDeviceInfo(params);
}

export async function makeFixId(params) {
    return await RNReactNativeMencryptSign.makeFixId(params);
}
