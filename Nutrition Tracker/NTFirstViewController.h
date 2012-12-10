//
//  NTFirstViewController.h
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-07.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import <UIKit/UIKit.h>



@interface NTFirstViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>
@property (strong, nonatomic) IBOutlet UITableView *FoodGroupsDescriptionTableView;
@property (strong, nonatomic) NSMutableArray *FoodGroupDescriptions;
@property (strong, nonatomic) NSString *searchText;
@end
