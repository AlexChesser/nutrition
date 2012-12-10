//
//  NTFoodInfoViewController.h
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-09.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NTAppDelegate.h"

@interface NTFoodInfoViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) NSDictionary *FoodItem;
@property (strong, nonatomic) NSArray *FoodData;
@property (strong, nonatomic) NSArray *Weights;
@property (strong, nonatomic) IBOutlet UINavigationBar *nav;
@property (strong, nonatomic) IBOutlet UITextView *FoodTitle;
@property (strong, nonatomic) IBOutlet UITableView *DataTable;
@property (strong, nonatomic) IBOutlet UITextField *WeightAmount;

-(void) setFoodID: (NSString*) foodID;



- (IBAction)done:(id)sender;
- (IBAction)weightChange:(id)sender;

@end
