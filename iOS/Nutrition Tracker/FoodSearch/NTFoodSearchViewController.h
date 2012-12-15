//
//  NTFoodSearchViewController.h
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-09.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NTAppDelegate.h"


@interface NTFoodSearchViewController : UIViewController <UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate>

@property (strong, nonatomic) IBOutlet UISearchBar *SearchBar;
@property (strong, nonatomic) NSArray *SearchResults;
@property (strong, nonatomic) IBOutlet UITableView *ResultTable;


@end
