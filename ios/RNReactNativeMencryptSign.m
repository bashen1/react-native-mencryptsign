#import "RNReactNativeMencryptSign.h"
#import "mmbKey.h"

@implementation RNReactNativeMencryptSign {
    bool hasListeners;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
+ (BOOL) requiresMainQueueSetup {
    return YES;
}
RCT_EXPORT_MODULE()

// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

RCT_EXPORT_METHOD(makeSign:(NSDictionary *)param resolve: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    NSDictionary *result = [mmbKey getToken:param];
    NSDictionary *ret = @{@"code": @"1", @"sign": result[@"sign"], @"paramStr": result[@"paramStr"]};
    resolve(ret);
}

@end

