/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.configuration;

import junit.framework.Assert;
import org.junit.Test;
import org.neo4j.graphdb.factory.GraphDatabaseSetting;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.helpers.collection.MapUtil;

/**
 * Test for SystemPropertiesConfiguration
 */
public class SystemPropertiesConfigurationTest
{
    @Test
    public void testThatSetValidSystemPropertiesArePickedUp()
    {
        try
        {
            Assert.assertFalse( MapUtil.stringMap( GraphDatabaseSettings.read_only.name(), GraphDatabaseSetting.TRUE ).equals(
                                 new SystemPropertiesConfiguration( GraphDatabaseSettings.class ).apply( MapUtil.stringMap(  ) )));

            System.setProperty( GraphDatabaseSettings.read_only.name(), GraphDatabaseSetting.TRUE );

            Assert.assertEquals( MapUtil.stringMap( GraphDatabaseSettings.read_only.name(), GraphDatabaseSetting.TRUE ),
                                 new SystemPropertiesConfiguration( GraphDatabaseSettings.class ).apply( MapUtil.stringMap(  ) ));

            System.setProperty( GraphDatabaseSettings.read_only.name(), "foo" );

            Assert.assertEquals( MapUtil.stringMap( ),
                                 new SystemPropertiesConfiguration( GraphDatabaseSettings.class ).apply( MapUtil.stringMap(  ) ));
        }
        finally
        {
            System.clearProperty( GraphDatabaseSettings.read_only.name() );
        }
    }

    @Test
    public void testThatSetInvalidSystemPropertiesAreNotPickedUp()
    {
        System.setProperty( "foo", "bar" );

        Assert.assertEquals( MapUtil.stringMap(  ),
                             new SystemPropertiesConfiguration( GraphDatabaseSettings.class ).apply( MapUtil.stringMap(  ) ));
    }
}
