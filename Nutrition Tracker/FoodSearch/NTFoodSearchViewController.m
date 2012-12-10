//
//  NTFoodSearchViewController.m
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-09.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import "NTFoodSearchViewController.h"
#import "NTFoodInfoViewController.h"


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

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
    if ([self.SearchResults count] == 0) {
        return 1;
    }
    return [self.SearchResults count];
    
}

- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText {
    NTAppDelegate *appDelegate = (NTAppDelegate *)[[UIApplication sharedApplication] delegate];
    self.SearchResults = [appDelegate getQuery:[NSString stringWithFormat: @"SELECT * FROM FOOD_DES WHERE Long_Desc LIKE '%%%@%%' ORDER BY Shrt_Desc ASC", searchText]];
    
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
