//
//  NTAppDelegate.h
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-07.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FMDatabase.h"
#import "FMDatabaseAdditions.h"
#import "FMDatabasePool.h"
#import "FMDatabaseQueue.h"
#import "FMResultSet.h"

@interface NTAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) NSString *databaseName;
@property (strong, nonatomic) NSString *databasePath;
@property (strong, nonatomic) NSString *addFilter;
@property (strong, nonatomic) FMDatabase *database;


- (NSArray *)getQuery: (NSString *) sql;


@end
