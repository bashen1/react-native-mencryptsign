#import "mmbKey.h"
#import "RNReactNativeMencryptSign.h"

@implementation RNReactNativeMencryptSign {
    bool hasListeners;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

RCT_EXPORT_MODULE()

// Will be called when this module's first listener is added.
- (void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
- (void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

RCT_EXPORT_METHOD(makeSign:(NSDictionary *)param resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    @try {
        NSDictionary *result = [mmbKey getToken:param];
        NSDictionary *ret = @{
                @"code": @"1",
                @"sign": result[@"sign"],
                @"paramStr": result[@"paramStr"],
                @"errName": @"",
                @"errMessage": @""
        };

        resolve(ret);
    } @catch (NSException *e) {
        NSDictionary *ret = @{
                @"code": @"0",
                @"sign": @"",
                @"paramStr": @"",
                @"errName": e.name,
                @"errMessage": e.reason
        };
        resolve(ret);
    }
}

RCT_EXPORT_METHOD(makeDeviceInfo:(NSDictionary *)param resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    @try {
        NSDictionary *result = [mmbKey getDeviceInfo:param];
        NSDictionary *ret = @{
                @"code": @"1",
                @"sessionId": result[@"sessionId"],
                @"deviceInfo": result[@"deviceInfo"],
                @"errName": @"",
                @"errMessage": @""
        };
        resolve(ret);
    } @catch (NSException *e) {
        NSDictionary *ret = @{
                @"code": @"0",
                @"sessionId": @"",
                @"deviceInfo": @"",
                @"errName": e.name,
                @"errMessage": e.reason
        };
        resolve(ret);
    }
}

RCT_EXPORT_METHOD(makeFixId:(NSDictionary *)param resolve:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    @try {
        NSDictionary *result = [mmbKey getFixId:param];
        NSDictionary *ret = @{
                @"systemDevId": result[@"systemDevId"],
                @"fixDevId": result[@"fixDevId"],
                @"errName": @"",
                @"errMessage": @""
        };
        resolve(ret);
    } @catch (NSException *e) {
        NSDictionary *ret = @{
                @"systemDevId": @"",
                @"fixDevId": @"",
                @"errName": e.name,
                @"errMessage": e.reason
        };
        resolve(ret);
    }
}

@end
