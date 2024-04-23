//
//  mmbKey.h
//  mmbKey
//
//  Created by IORI on 2021/2/1.
//

#import <Foundation/Foundation.h>

@interface mmbKey : NSObject

+ (NSDictionary *)getToken:(NSDictionary *)param;

+ (NSDictionary *)getDeviceInfo:(NSDictionary *)param;

+ (NSDictionary *)getFixId:(NSDictionary *)param;

@end
