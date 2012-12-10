//
//  NTFirstViewController.m
//  Nutrition Tracker
//
//  Created by Application Developer on 12-12-07.
//  Copyright (c) 2012 Alex Chesser. All rights reserved.
//

#import "NTFirstViewController.h"
#import "NTAppDelegate.h"


@interface NTFirstViewController ()

@end

@implementation NTFirstViewController
@synthesize FoodGroupsDescriptionTableView = _FoodGroupsDescriptionTableView;
@synthesize FoodGroupDescriptions = _FoodGroupDescriptions;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    NTAppDelegate *appDelegate = (NTAppDelegate *)[[UIApplication sharedApplication] delegate];
    FMDatabase *db = appDelegate.database;
    [db open];
    
    FMResultSet *results = [db executeQuery:@"SELECT * FROM FD_GROUP ORDER BY FdGrp_desc ASC"];
    
    self.FoodGroupDescriptions = [self.FoodGroupDescriptions initWithArray:nil];
    
    NSMutableArray *arr = [[NSMutableArray alloc] initWithArray:nil];
    
    while([results next])
    {
        [arr addObject:[results stringForColumn:@"FdGrp_desc"]];
    }
    self.FoodGroupDescriptions = arr;
    [db close];
    

    
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
    NSLog(@"%@",self.FoodGroupDescriptions);
    return [self.FoodGroupDescriptions count];
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"FoodGroupDescriptionCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    //    NSLog(@"%@",[self.FoodGroupDescriptions objectAtIndex:indexPath.row]);
    cell.textLabel.text = [self.FoodGroupDescriptions objectAtIndex:indexPath.row];
    return cell;
}

/*
 // Override to support conditional editing of the table view.
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */

/*
 // Override to support editing the table view.
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
 }
 else if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }
 }
 */

/*
 // Override to support rearranging the table view.
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
 {
 }
 */

/*
 // Override to support conditional rearranging of the table view.
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */

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
}




@end
