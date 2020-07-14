#import "RNReactNativeMencryptSign.h"
#import <CommonCrypto/CommonCrypto.h>

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
    NSString *secret = (NSString *)param[@"secret"];
    NSDictionary *params = param[@"params"];
    NSMutableString *contentString =[NSMutableString string];
    NSArray *keys = [params allKeys];
    
    //参数名称的ASCII码表的顺序排序
    NSArray *sortedArray = [keys sortedArrayUsingComparator:^NSComparisonResult(id obj1, id obj2) {
        return [obj1 compare:obj2 options:NSNumericSearch];
    }];
    
    //参数名和参数值拼装在一起，过滤掉空的
    for (NSString *categoryId in sortedArray) {
        if ( ![[params objectForKey:categoryId] isEqualToString:@""])  {
            [contentString appendFormat:@"%@%@", categoryId, [params objectForKey:categoryId]];
        }
    }
    
    //首尾加上secret
    NSMutableString *verifyString = [NSMutableString string];
    [verifyString appendString:secret];
    [verifyString appendString:contentString];
    [verifyString appendString:secret];
    NSString *verifyRes = [verifyString uppercaseString];
    //MD5 编码
    NSString *md5Res = [self md5HexDigest:verifyRes];
    NSDictionary *ret = @{@"code": @"1", @"sign": md5Res, @"paramStr": verifyRes, @"secret": secret};
    resolve(ret);
}


-(NSString *)md5HexDigest:(NSString *) str {
    const char *original_str = [str UTF8String];//转化为c的字符串
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    unsigned int ad = (int)strlen(original_str);
    CC_MD5(original_str,ad, result);
    NSMutableString *hash = [NSMutableString string];
    for (int i = 0; i < 16; i++)
        [hash appendFormat:@"%02X", result[i]];
    return [hash uppercaseString];//将加密后的字符串中的字母改为小写(大写uppercaseString)
}

@end
