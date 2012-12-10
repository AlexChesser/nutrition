//
//  NTFoodSearchViewController.m
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-09.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import "NTFoodSearchViewController.h"
#import "NTFoodInfoViewController.h"
#import "NTFirstViewController.h"

@interface NTFoodSearchViewController ()

@end

@implementation NTFoodSearchViewController
@synthesize SearchBar = _SearchBar;
@synthesize SearchResults = _SearchResults;
@synthesize ResultTable = _ResultTable;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
}

-(void) viewWillAppear:(BOOL)animated{
    [self fetchResults];
    [self.ResultTable reloadData];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)searchBarResultsListButtonClicked:(UISearchBar *)searchBar {
    UIStoryboard*  sb = [UIStoryboard storyboardWithName:@"MainStoryboard_iPhone"
                                                  bundle:nil];
    NTFirstViewController *vc = [sb instantiateViewControllerWithIdentifier:@"NTFirstViewController"];
    vc.searchText = self.SearchBar.text;
    [self presentViewController: vc animated:YES completion:^{}];
    
    
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.SearchResults count];
    
}

- (void) fetchResults{
    NTAppDelegate *appDelegate = (NTAppDelegate *)[[UIApplication sharedApplication] delegate];
    NSString *addWhere = @"";
    if (appDelegate.addFilter != @"" && appDelegate.addFilter != @"None") {
        addWhere = [NSString stringWithFormat:@" AND fg.FdGrp_Desc = '%@'", appDelegate.addFilter];
    }

    NSString *sql = [NSString stringWithFormat: @"SELECT fd.*, fg.FdGrp_Desc FROM FOOD_DES fd INNER JOIN FD_GROUP fg ON fd.FdGrp_Cd = fg.FdGrp_Cd WHERE Long_Desc LIKE '%%%@%%' %@ ORDER BY FdGrp_Desc, Shrt_Desc ASC", self.SearchBar.text, addWhere];
    self.SearchResults = [appDelegate getQuery:sql];
    
}

- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText {
    [self fetchResults];
    [self.ResultTable reloadData];

}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"FoodSearchResultsCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    UIFont *myFont = [ UIFont fontWithName: @"Arial" size: 12.0 ];
    cell.textLabel.font  = myFont;
    cell.textLabel.text = @"Search for a food item";
    // Configure the cell...
    if ([self.SearchResults count] > 0) {
        NSDictionary *fi = [self.SearchResults objectAtIndex:indexPath.row];
        cell.textLabel.text = [fi valueForKey:@"Long_Desc"];
        cell.detailTextLabel.text = [fi valueForKey:@"FdGrp_Desc"];
        cell.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;

    } else {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
    return cell;
}

- (void)tableView:(UITableView *)tableView accessoryButtonTappedForRowWithIndexPath:(NSIndexPath *)indexPath {
    NSLog(@"tapped");
    
    UIStoryboard*  sb = [UIStoryboard storyboardWithName:@"MainStoryboard_iPhone"
                                                  bundle:nil];
    NTFoodInfoViewController* vc = [sb instantiateViewControllerWithIdentifier:@"NTFoodInfoViewController"];
    
    NSDictionary *fi = [self.SearchResults objectAtIndex:indexPath.row];
    [vc setFoodID:[fi valueForKey:@"NDB_No"]];
    
    [self presentViewController: vc animated:YES completion:^{}];

    
    
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
    [self.SearchBar resignFirstResponder];
}



@end
