using ProjectManagementTool.classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ProjectManagementTool
{
    /// <summary>
    /// Interaction logic for Main.xaml
    /// </summary>
    public partial class Main : Window
    {
        private Kontext ktx = new Kontext();
        private User currentUser;

        public Main(User user)
        {
            InitializeComponent();
            this.currentUser = user;
            loadGUI();

            generateListItems(20);
        }

        private void generateListItems(int numberOfItems)
        {
            for (int i=0; i<numberOfItems; i++)
            {
                ListBoxItem item = new ListBoxItem();
                item.Content = "Item " + i;
                item.FontSize = 30;
                item.Height = 50;
               

                projectList.Items.Add(item);
            }
        }

        private void loadGUI()
        {
            lblProfile.Inlines.Clear();
            lblProfile.Inlines.Add(currentUser.Username);
        }
    }
}
